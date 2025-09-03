import { useEffect, useState, useCallback } from 'react';
import Header from './components/Header';
import Toolbar from './components/Toolbar';
import DataTable, { SkeletonTable } from './components/DataTable';
import Modal from './components/Modal';
import Toasts from './components/Toasts';
import { toast } from './utils/toast';
import useDebounced from './hooks/useDebounced';
import { fetchPage, createProduct, updateProduct, deleteProduct, parseIdFromLocation } from './api';
import './styles.css';

export default function App() {
  // Query parameters state
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(10);
  const [sort, setSort] = useState('id,desc');
  const [q, setQ] = useState('');
  const debouncedQ = useDebounced(q, 300);

  // Data state
  const [content, setContent] = useState([]);
  const [total, setTotal] = useState(0);
  const [lastPage, setLastPage] = useState(0);
  const [loading, setLoading] = useState(false);

  // UI state
  const [modalOpen, setModalOpen] = useState(false);
  const [editing, setEditing] = useState(null);   // if not null, this is an existing product being edited
  const [highlightId, setHighlightId] = useState(null);

  // Function to load a page of products from the API
  const loadData = useCallback(async () => {
    setLoading(true);
    try {
      const pageData = await fetchPage({ page, size, sort, q: debouncedQ });
      setContent(pageData.content || []);
      setTotal(pageData.totalElements ?? 0);
      setLastPage((pageData.totalPages || 1) - 1);
    } catch (error) {
      console.error('Failed to fetch products:', error);
      toast('Failed to load products', 'err');
    } finally {
      setLoading(false);
    }
  // We intentionally include debouncedQ (the value that changes after user stops typing)
  // and not 'q', so that we only load after debouncing
  }, [page, size, sort, debouncedQ]);

  // Reset to first page whenever search, size, or sort changes (to fetch fresh from beginning)
  useEffect(() => {
    setPage(0);
  }, [debouncedQ, size, sort]);

  // Fetch data whenever page or other query params (debouncedQ, size, sort) change
  useEffect(() => {
    loadData();
  }, [loadData]);

  // Handle create or update submission from Modal
  const handleSubmit = async (productData) => {
    if (editing) {
      // Update existing product
      await updateProduct(editing.id, productData);
      setHighlightId(String(editing.id));
      toast('Updated successfully', 'ok');
    } else {
      // Create new product
      const { location } = await createProduct(productData);
      // parse new ID from Location header and highlight it
      const newId = parseIdFromLocation(location);
      if (newId) setHighlightId(String(newId));
      // After creating, we want to show the first page (which likely contains the new product if sorted by desc ID)
      setPage(0);
      toast('Created successfully', 'ok');
    }
    // After create or update, reload data (the effect will run because page or debouncedQ might change, but to be sure we call loadData directly)
    await loadData();
  };

  // Handle delete action
  const handleDelete = async (id) => {
    const confirmMsg = `Delete product #${id}?`;
    if (!window.confirm(confirmMsg)) return;
    try {
      await deleteProduct(id);
      toast('Deleted product', 'ok');
      // If we deleted the last item on a page and it was not the first page, we might want to load the previous page.
      // Simplicity: just reload current page, which will have one less item (if that was last item and we aren't on first page, we might see an empty page, but user can navigate back).
      await loadData();
    } catch (error) {
      console.error('Delete failed:', error);
      toast(error.message || 'Failed to delete product', 'err');
    }
  };

  // Effect to highlight a newly created/updated product row
  useEffect(() => {
    if (!highlightId) return;
    // After each data load (content change), if highlightId is set, highlight that row
    const selector = `[data-row-id="${CSS.escape(highlightId)}"]`;
    const element = document.querySelector(selector);
    if (element) {
      element.classList.add('highlight');
      element.scrollIntoView({ behavior: 'smooth', block: 'center' });
      const removeHighlight = setTimeout(() => {
        element.classList.remove('highlight');
      }, 1800);
      // Cleanup in case the content updates or component unmounts before timeout
      return () => clearTimeout(removeHighlight);
    }
  }, [content, highlightId]);

  // Derive a label for item count (for potential use, though we used itemCountLabel inside Toolbar as well, so we comment it out unless we need it later in different parts of the app).
  // const totalCountLabel = useMemo(() => {
  //   return `${total} item${total !== 1 ? 's' : ''}`;
  // }, [total]);

  return (
    <>
      {/* Header with create button */}
      <Header onCreate={() => { setEditing(null); setModalOpen(true); }} />

      {/* Toolbar with search, sort, pagination controls */}
      <Toolbar
        q={q} setQ={setQ}
        sort={sort} setSort={setSort}
        size={size} setSize={(val) => { setSize(val); /* reset page handled by effect */ }}
        page={page} lastPage={lastPage} total={total}
        onPrev={() => setPage(p => Math.max(0, p - 1))}
        onNext={() => setPage(p => Math.min(lastPage, p + 1))}
      />

      {/* Main content area */}
      <main className="container">
        {loading ? (
          <SkeletonTable />
        ) : (
          <DataTable
            rows={content}
            onEdit={(product) => { setEditing(product); setModalOpen(true); }}
            onDelete={handleDelete}
          />
        )}
      </main>

      {/* Modal for create/edit product */}
      <Modal
        open={modalOpen}
        onClose={() => setModalOpen(false)}
        title={editing ? `Edit #${editing.id}` : 'New Product'}
        onSubmit={handleSubmit}
        initial={editing}
      />

      {/* Toasts container (and toast function already imported) */}
      <Toasts />
    </>
  );
}
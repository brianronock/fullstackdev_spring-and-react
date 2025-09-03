// const API_BASE = import.meta.env.VITE_API_BASE || 'http://localhost:8080/api/products';
const API_BASE = import.meta.env.VITE_API_BASE ||'http://192.168.0.40:8080/api/products'

// Utility: construct query string from an object of parameters
const qs = (params) =>
  Object.entries(params)
    .filter(([, v]) => v !== undefined && v !== null && v !== '')
    .map(([k, v]) => `${encodeURIComponent(k)}=${encodeURIComponent(v)}`)
    .join('&');

// Currency formatter for prices (Euro currency in Austrian locale for € formatting)
export const fmtCurrency = new Intl.NumberFormat('de-AT', { style: 'currency', currency: 'EUR' });

// GET list of products (with optional search query)
export async function fetchPage({ page = 0, size = 10, sort = 'id,desc', q = '' } = {}) {
  const baseUrl = q ? `${API_BASE}/search` : API_BASE;
  const query = { page, size, sort, ...(q ? { q } : {}) };
  const url = `${baseUrl}?${qs(query)}`;

  const res = await fetch(url);
  if (!res.ok) {
    // For any HTTP error (like 500), throw an Error to be caught by caller
    throw new Error(`HTTP ${res.status}`);
  }
  return res.json(); 
  // expected to be a Spring Data Page object: { content: [...], totalElements: X, totalPages: Y, number: currentPage, ... }
}

// POST create a new product
export async function createProduct(payload) {
  const res = await fetch(API_BASE, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload),
  });
  if (res.status === 400) {
    // Validation error – backend sends a JSON map of field errors
    const errorData = await res.json().catch(() => ({}));
    const err = new Error('Validation failed');
    err.validation = errorData;   // attach validation errors (e.g., { name: "Name is mandatory" })
    throw err;
  }
  if (!res.ok) {
    // Some other error (shouldn't happen in normal use for create, except maybe 500)
    const message = await res.text();
    throw new Error(message || `HTTP ${res.status}`);
  }
  const location = res.headers.get('Location'); // e.g. "/api/products/42"
  return { location };
}

// PUT update an existing product by ID
export async function updateProduct(id, payload) {
  const res = await fetch(`${API_BASE}/${encodeURIComponent(id)}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload),
  });
  if (res.status === 400) {
    const errorData = await res.json().catch(() => ({}));
    const err = new Error('Validation failed');
    err.validation = errorData;
    throw err;
  }
  if (!res.ok) {
    const message = await res.text();
    throw new Error(message || `HTTP ${res.status}`);
  }
  // If OK, we don't need to return JSON (backend might return updated entity, but we can ignore or use it if needed)
}

// DELETE a product by ID
export async function deleteProduct(id) {
  const res = await fetch(`${API_BASE}/${encodeURIComponent(id)}`, { method: 'DELETE' });
  if (!res.ok) {
    const message = await res.text();
    throw new Error(message || `HTTP ${res.status}`);
  }
  // No return needed if successful (204 No Content expected)
}

// Helper: parse numeric ID from Location URL
export function parseIdFromLocation(locationUrl) {
  if (!locationUrl) return null;
  try {
    const url = new URL(locationUrl, window.location.origin);
    const parts = url.pathname.split('/');
    return parts[parts.length - 1] || null;
  } catch {
    // If locationUrl is relative or invalid, fallback to string parsing
    const parts = locationUrl.split('/');
    return parts[parts.length - 1] || null;
  }
}

export default function Toolbar({
  q, setQ,
  sort, setSort,
  size, setSize,
  page, lastPage, total,
  onPrev, onNext
}) {
  const itemCountLabel = `${total} item${total !== 1 ? 's' : ''}`;

  return (
    <section className="toolbar">
      {/* Left side: search and total count */}
      <div className="left">
        <div className="input-icon">
          {/* Search icon SVG for visual hint */}
          <svg viewBox="0 0 24 24" aria-hidden="true">
            <path d="M15.5 14h-.79l-.28-.27A6.471 6.471 0 0 0 16 9.5 
                     6.5 6.5 0 1 0 9.5 16c1.61 0 3.09-.59 
                     4.23-1.57l.27.28v.79l4.25 4.25 
                     1.5-1.5L15.5 14Zm-6 0C7.01 14 
                     5 11.99 5 9.5S7.01 5 9.5 5 
                     14 7.01 14 9.5 11.99 14 9.5 14Z"/>
          </svg>
          <input 
            type="text"
            value={q}
            onChange={(e) => setQ(e.target.value)}
            placeholder="Search by name…"
          />
        </div>
        <span className="chip muted">{itemCountLabel}</span>
      </div>

      {/* Right side: sort, size, and pager */}
      <div className="right">
        <label>Sort:{" "}
          <select value={sort} onChange={(e) => setSort(e.target.value)}>
            <option value="id,desc">ID ↓ (Newest)</option>
            <option value="id,asc">ID ↑ (Oldest)</option>
            <option value="name,asc">Name A→Z</option>
            <option value="name,desc">Name Z→A</option>
            <option value="price,asc">Price ↑ (Lowest)</option>
            <option value="price,desc">Price ↓ (Highest)</option>
          </select>
        </label>

        <label>Size:{" "}
          <select value={size} onChange={(e) => setSize(Number(e.target.value))}>
            <option value="5">5</option>
            <option value="10">10</option>
            <option value="20">20</option>
            <option value="50">50</option>
          </select>
        </label>

        <div className="pager">
          <button className="btn btn-ghost" onClick={onPrev} disabled={page <= 0}>
            ◀ Prev
          </button>
          <span className="muted">Page {page + 1} of {lastPage + 1}</span>
          <button className="btn btn-ghost" onClick={onNext} disabled={page >= lastPage}>
            Next ▶
          </button>
        </div>
      </div>
    </section>
  );
}
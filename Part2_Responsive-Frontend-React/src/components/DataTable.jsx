import { fmtCurrency } from '../api';

export default function DataTable({ rows, onEdit, onDelete }) {
  if (!rows.length) {
    // No products case:
    return (
      <div className="table-wrap">
        <div className="empty">
          <div className="empty-icon">📭</div>
          <h3>No products</h3>
          <p className="muted">Create your first product to get started.</p>
        </div>
      </div>
    );
  }

  return (
    <>
      {/* Desktop Table */}
      <div className="table-wrap desktop-only">
        <table className="table">
          <thead>
            <tr>
              <th style={{ width: '50px' }}>ID</th>
              <th>Name</th>
              <th style={{ width: '120px' }}>Price</th>
              <th style={{ width: '160px' }}>Actions</th>
            </tr>
          </thead>
          <tbody>
            {rows.map(product => (
              <tr key={product.id} data-row-id={product.id}>
                <td><span className="badge">#{product.id}</span></td>
                <td className="truncate">{product.name}</td>
                <td><strong>{fmtCurrency.format(Number(product.price))}</strong></td>
                <td>
                  <div className="actions-row">
                    <button className="btn btn-ghost" onClick={() => onEdit(product)}>Edit</button>
                    <button className="btn btn-ghost" onClick={() => onDelete(product.id)}>Delete</button>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {/* Mobile Cards */}
      <ul className="cards mobile-only">
        {rows.map(product => (
          <li key={product.id} className="card" data-row-id={product.id}>
            <div className="card-top">
              <div className="card-title truncate">{product.name}</div>
              <div className="card-price">{fmtCurrency.format(Number(product.price))}</div>
            </div>
            <div className="card-meta">ID: #{product.id}</div>
            <div className="card-actions">
              <button className="btn btn-ghost" onClick={() => onEdit(product)}>Edit</button>
              <button className="btn btn-ghost" onClick={() => onDelete(product.id)}>Delete</button>
            </div>
          </li>
        ))}
      </ul>
    </>
  );
}

// Skeleton loading placeholder
export function SkeletonTable() {
  const fakeRows = Array.from({ length: 5 });

  return (
    <>
      <div className="table-wrap desktop-only">
        <table className="table">
          <thead>
            <tr>
              <th>ID</th><th>Name</th><th>Price</th><th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {fakeRows.map((_, idx) => (
              <tr className="skeleton" key={idx}>
                <td></td><td></td><td></td><td></td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      <ul className="cards mobile-only">
        {fakeRows.map((_, idx) => (
          <li className="card skeleton-card" key={idx}>
            <div className="sk-line"></div>
            <div className="sk-line short"></div>
            <div className="sk-line sk-actions"></div>
          </li>
        ))}
      </ul>
    </>
  );
}
export default function Header({ onCreate }) {
  return (
    <header className="app-header">
      <div className="brand">
        <div className="logo">🛒</div>
        <div>
          <h1>Products</h1>
          <p className="muted">Manage your catalog</p>
        </div>
      </div>
      <div className="header-actions">
        <button className="btn btn-primary" onClick={onCreate}>
          ＋ New Product
        </button>
      </div>
    </header>
  );
}
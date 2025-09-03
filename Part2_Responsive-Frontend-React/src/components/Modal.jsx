import { useEffect, useRef, useState } from 'react';

export default function Modal({ open, onClose, title, onSubmit, initial }) {
  const [name, setName] = useState(initial?.name ?? '');
  const [price, setPrice] = useState(initial?.price ?? '');
  const [errors, setErrors] = useState({});
  const modalRef = useRef(null);

  useEffect(() => {
    if (open) {
      // Reset form fields to initial values each time modal opens
      setName(initial?.name ?? '');
      setPrice(initial?.price ?? '');
      setErrors({});
      // Autofocus the first input after a short delay (to ensure modal content is rendered)
      const focusTimer = setTimeout(() => {
        modalRef.current?.querySelector('input')?.focus();
      }, 10);
      // Prevent background scroll
      document.body.style.overflow = 'hidden';
      return () => {
        clearTimeout(focusTimer);
        document.body.style.overflow = '';
      };
    }
  }, [open, initial]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setErrors({});
    try {
      // Trim name, convert price to number (or BigDecimal representation if needed)
      const payload = { name: name.trim(), price: parseFloat(price) };
      await onSubmit(payload);
      // If successful, close modal
      onClose();
    } catch (err) {
      if (err.validation) {
        // Display field-specific errors
        setErrors(err.validation);
      } else {
        // Display general error (we can use alert or a toast; for simplicity alert here)
        alert(err.message || 'Failed to save product');
      }
    }
  };

  if (!open) return null;

  return (
    <div className="modal-backdrop" onClick={onClose}>
      <div className="modal-card" onClick={(e) => e.stopPropagation()} ref={modalRef}>
        <h3>{title}</h3>
        <form onSubmit={handleSubmit}>
          <div className="form-grid">
            <label>
              Name
              <input 
                type="text"
                value={name}
                onChange={(e) => setName(e.target.value)}
                required 
                maxLength={120}
              />
              <small className="field-error">{errors.name || ''}</small>
            </label>
            <label>
              Price
              <input 
                type="number"
                value={price}
                onChange={(e) => setPrice(e.target.value)}
                step="0.01" min="0"
                required 
              />
              <small className="field-error">{errors.price || ''}</small>
            </label>
          </div>
          <div className="modal-actions">
            <button type="button" className="btn btn-ghost" onClick={onClose}>
              Cancel
            </button>
            <button type="submit" className="btn btn-primary">
              Save
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

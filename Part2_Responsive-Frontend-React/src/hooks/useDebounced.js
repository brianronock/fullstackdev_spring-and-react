import { useState, useEffect } from 'react';

export default function useDebounced(value, delay = 300) {
  const [debouncedValue, setDebouncedValue] = useState(value)

  useEffect(() => {
    const handler = setTimeout(() => {
      setDebouncedValue(value)
    }, delay);

    // Cancel the timeout if value or delay changes, or on unmount
    return () => {
      clearTimeout(handler)
    };
  }, [value, delay])

  return debouncedValue
}
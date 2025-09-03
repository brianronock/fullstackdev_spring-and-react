export function toast(message, type = 'ok') {
    const container = document.getElementById('toasts')
    if (!container) return;
    const el = document.createElement('div')
    el.className = `toast ${type === 'err' ? 'err' : 'ok'}`
    el.innerText = message
    container.appendChild(el)
    setTimeout(() => {
        el.remove()
    }, 2500)    // remove after 2.5 seconds
}
import { useEffect } from "react";

/** This function has been moved to /src/utils/toast.js. To be imported in App.jsx (import { toast } from './utils/toast')*/
// export function toast(message, type = 'ok') {
//     const container = document.getElementById('toasts')
//     if (!container) return;
//     const el = document.createElement('div')
//     el.className = `toast ${type === 'err' ? 'err' : 'ok'}`
//     el.innerText = message
//     container.appendChild(el)
//     setTimeout(() => {
//         el.remove()
//     }, 2500)    // remove after 2.5 seconds
// }

export default function Toasts() {
    useEffect(() => {
                // on mount, ensure a container exists
        let container = document.getElementById('toasts')
        if (!container) {
            container = document.createElement('div')
            container.id = 'toasts'
            container.className = 'toasts'
            document.body.appendChild(container)
        }
    }, [])      // no cleanup needed on unmount(we leave the container in DOM), hence empty dependency array
    return null // this component doesn't render anything visible by itself
}
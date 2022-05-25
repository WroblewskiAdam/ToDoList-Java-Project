import { useEffect } from 'react';

const useOuterClick = (nodeRef, action) => {
    useEffect(() => {
        const handleClickOutside = (e) => {
            if(nodeRef.current && !nodeRef.current.contains(e.target)){
                action(e);
            }
        }
        document.addEventListener("click", handleClickOutside, true);
        return () => {
            document.removeEventListener("click", handleClickOutside, true);
        };
    }, [nodeRef, action]);
}

export default useOuterClick;
import { useEffect, useRef } from 'react';

// const useClickOutside = (action) => {
//     const nodeRef = useRef();

//     useEffect(() => {
//         let afterClick = (e) => {
//             e.preventDefault();
//             if(nodeRef.current && !nodeRef.current.contains(e.target)){
//                 action();
//             }
//         }

//         document.addEventListener("mousedown", afterClick);

//         return () => {
//             document.removeEventListener("mousedown", afterClick);
//         };
//     });

//     return nodeRef;
// }

// const useClickOutside = (nodeRef, action) => {
//     useEffect(() => {
//         const afterClick = (e) => {
//             if(nodeRef.current && !nodeRef.current.contains(e.target)){
//                 action();
//             }
//         }

//         document.addEventListener("mousedown", afterClick);

//         return () => {
//             document.removeEventListener("mousedown", afterClick);
//         };
//     }, [nodeRef]);
// }
// const OutsideAlertBlock = ({children, action}) => {
//     const nodeRef = useRef();
//     useClickOutside(nodeRef, action);

//     return (
//         <div ref="nodeRef">{children}</div>
//     );
// }

// const useOuterClick = (callback) => {
//     const callbackRef = useRef(); // initialize mutable ref, which stores callback
//     const innerRef = useRef(); // returned to client, who marks "border" element
  
//     // update cb on each render, so second useEffect has access to current value 
//     useEffect(() => { callbackRef.current = callback; });
    
//     useEffect(() => {
//       document.addEventListener("click", handleClick);
//       return () => document.removeEventListener("click", handleClick);
//       function handleClick(e) {
//         if (innerRef.current && callbackRef.current && !innerRef.current.contains(e.target)){
//             callbackRef.current(e);
//         }
//       }
//     }, []); // no dependencies -> stable click listener
        
//     return innerRef; // convenience for client (doesn't need to init ref himself) 
//   }

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
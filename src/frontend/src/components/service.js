import React from 'react';

function Service(props) {

    const getData = () => {
        fetch("https://localhost:8080/edit/form")
            .then(res => res.json())
    }
    return (
        <div></div>
    );
}

export default Service;
import React from 'react';
import BasicGroupListItem from '../basicGroupList-item/BasicGroupListItem';
import './BasicGroupList.scss';
import { useState, useEffect } from 'react';
const BasicGroupList = () => {
    const [date, setDate] = useState(null);

    useEffect(() => {
        let newDate = new Date();
        setDate(newDate.getDate());
    }, [])
    return (
        <div className='basicGroupList'>
            <div className="basicGroupList__block">
                <BasicGroupListItem title="All" path="M464 96h-192l-64-64h-160C21.5 32 0 53.5 0 80V160h512V144C512 117.5 490.5 96 464 96zM0 432C0 458.5 21.5 480 48 480h416c26.5 0 48-21.5 48-48V192H0V432z" count="12"/>
                <BasicGroupListItem title="Today" path="M96 32C96 14.33 110.3 0 128 0C145.7 0 160 14.33 160 32V64H288V32C288 14.33 302.3 0 320 0C337.7 0 352 14.33 352 32V64H400C426.5 64 448 85.49 448 112V160H0V112C0 85.49 21.49 64 48 64H96V32zM448 464C448 490.5 426.5 512 400 512H48C21.49 512 0 490.5 0 464V192H448V464z" iconText={date} count="12"/>
                <BasicGroupListItem title="Next 7 days" path="M96 32C96 14.33 110.3 0 128 0C145.7 0 160 14.33 160 32V64H288V32C288 14.33 302.3 0 320 0C337.7 0 352 14.33 352 32V64H400C426.5 64 448 85.49 448 112V160H0V112C0 85.49 21.49 64 48 64H96V32zM448 464C448 490.5 426.5 512 400 512H48C21.49 512 0 490.5 0 464V192H448V464z" iconText="D" count="12"/>
            </div>
        </div>
    );
};

export default BasicGroupList;
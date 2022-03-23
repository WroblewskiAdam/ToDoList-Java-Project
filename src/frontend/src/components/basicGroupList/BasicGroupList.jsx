import React from 'react';
import BasicGroupListItem from '../basicGroupList-item/BasicGroupListItem';
import './BasicGroupList.scss';

const BasicGroupList = () => {
    return (
        <div className='basicGroupList'>
            <div className="basicGroupList__title">
                Basic Group
            </div>
            <div className="basicGroupList__block">
                <BasicGroupListItem title="All" path="M464 96h-192l-64-64h-160C21.5 32 0 53.5 0 80V160h512V144C512 117.5 490.5 96 464 96zM0 432C0 458.5 21.5 480 48 480h416c26.5 0 48-21.5 48-48V192H0V432z" count="12"/>
                <BasicGroupListItem title="All" path="M464 96h-192l-64-64h-160C21.5 32 0 53.5 0 80V160h512V144C512 117.5 490.5 96 464 96zM0 432C0 458.5 21.5 480 48 480h416c26.5 0 48-21.5 48-48V192H0V432z" count="12"/>
                <BasicGroupListItem title="All" path="M464 96h-192l-64-64h-160C21.5 32 0 53.5 0 80V160h512V144C512 117.5 490.5 96 464 96zM0 432C0 458.5 21.5 480 48 480h416c26.5 0 48-21.5 48-48V192H0V432z" count="12"/>
                <BasicGroupListItem title="All" path="M464 96h-192l-64-64h-160C21.5 32 0 53.5 0 80V160h512V144C512 117.5 490.5 96 464 96zM0 432C0 458.5 21.5 480 48 480h416c26.5 0 48-21.5 48-48V192H0V432z" count="12"/>
            </div>
        </div>
    );
};

export default BasicGroupList;
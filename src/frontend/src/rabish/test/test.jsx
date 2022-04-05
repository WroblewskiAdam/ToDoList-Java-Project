import React, { useState } from 'react';
import Picker from 'emoji-picker-react';

const Test = () => {
  const [chosenEmoji, setChosenEmoji] = useState(null);

  const onEmojiClick = (event, emojiObject) => {
    console.log(emojiObject);
    setChosenEmoji(emojiObject);
  };

  return (
    <div>
      {chosenEmoji ? (
        <span>You chose: {chosenEmoji.emoji}</span>
      ) : (
        <span>No emoji Chosen</span>
      )}
      <Picker disableSearchBar={true} disableSkinTonePicker={true} disableAutoFocus={true} onEmojiClick={onEmojiClick} />
    </div>
  );
};

export default Test;
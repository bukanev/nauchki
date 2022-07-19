import React from "react";

export const FormSendFile = ({ uploadImage, sendFile }) => {
  return (
    <form action="#">
      <input
        onChange={uploadImage}
        name="files"
        type="file"
        accept=".jpg, .jpeg, .png, .gif, .bmp, .doc, .docx, .xls, .xlsx, .txt, .tar, .zip, .7z, .7zip"
        multiple
      />
      <button onClick={sendFile}>Отправить</button>
    </form>
  );
};

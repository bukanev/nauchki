import React, { useCallback, useState } from "react";
import { AdminAPI } from "../../api/api";

export const Adminka = () => {
  const [text, setText] = useState("");
  const [tag, setTag] = useState("");
  const [img, setImg] = useState(null);
  const [title, setTitle] = useState("");
  const [subtitle, setSubtitle] = useState("");

  const sendFile = useCallback(async () => {
    try {
      const data = new FormData();
      data.append("file", img);
      data.append("text", text);
      data.append("tag", tag);
      data.append("title", title);
      data.append("subtitle", subtitle);

      AdminAPI.addPost(data).then((res) => console.log(res.data));
    } catch (error) {
      console.log(error);
    }
  }, [text, tag, img, title, subtitle]);

  return (
    <div className="_wrapper area">
      <h1>Админка</h1>
      <form
        onSubmit={(e) => {
          e.preventDefault();
        }}
      >
        <textarea
          type="text"
          placeholder="Введите сообщение"
          onChange={(e) => setText(e.target.value)}
        ></textarea>
        <br />
        <input
          type="text"
          placeholder="Введите тэг"
          onChange={(e) => setTag(e.target.value)}
        ></input>
        <br />
        <input
          type="text"
          placeholder="Введите название"
          onChange={(e) => setTitle(e.target.value)}
        ></input>
        <br />
        <input
          type="text"
          placeholder="Введите подзаголовок"
          onChange={(e) => setSubtitle(e.target.value)}
        ></input>
        <br />
        <input type="file" onChange={(e) => setImg(e.target.files[0])}></input>
        <br />
        <button onClick={sendFile}>Отправить</button>
      </form>

      <br />
    </div>
  );
};

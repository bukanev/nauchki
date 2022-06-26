import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { addPostThunk } from '../../store/posts/actions';

const AdminArticlesForm = () => {
    const dispatch = useDispatch();

    const [text, setText] = useState(null);
    const [tag, setTag] = useState(null);
    const [img, setImg] = useState(null);
    const [title, setTitle] = useState(null);
    const [subtitle, setSubtitle] = useState(null);

    const uploadImage = (event) => {
        event.preventDefault();

        if (event.target.files[0]) {
            setImg(event.target.files[0])
        }
    }

    const uploadArticlesText = (event) => {
        event.preventDefault();

        if (event.target.name === 'articlesText') {
            setText(event.target.value);
        }
    }
    const uploadArticlesTag = (event) => {
        event.preventDefault();
        if (event.target.name === "articlesTag") {
            setTag(event.target.value)
        }
    }

    const uploadArticlesSubtitle = (event) => {
        event.preventDefault();

        if (event.target.name === 'articlesSubtitle') {
            setSubtitle(event.target.value)
        }
    }

    const uploadArticlesTitle = (event) => {
        event.preventDefault()

        if (event.target.name === 'articlesTitle') {
            setTitle(event.target.value);
        }
    }

    const sendFile = () => {
        const data = new FormData();

        data.append("file", img);
        data.append("text", text);
        data.append("tag", tag);
        data.append("title", title);
        data.append("subtitle", subtitle);

        dispatch(addPostThunk(data));
    }

    return (
        <form
            className="post__form"
            onSubmit={(e) => {
                e.preventDefault();
            }}
        >

            <label className="post__label" htmlFor="articlesTitle">Введите название</label>
            <input
                className="post__input"
                name="articlesTitle"
                type="text"
                placeholder="Введите название"
                onChange={uploadArticlesTitle}
            />
            <label className="post__label" htmlFor="articlesSubtitle">Введите подзаголовок</label>
            <input
                className="post__input"
                name="articlesSubtitle"
                type="text"
                placeholder="Введите подзаголовок"
                onChange={uploadArticlesSubtitle}
            />

            <label className="post__label" htmlFor="articlesTag">Введите тeг</label>
            <input
                className="post__input"
                name="articlesTag"
                type="text"
                placeholder="Введите тeг"
                onChange={uploadArticlesTag}
            />

            <label className="post__label" htmlFor="articlesText">Введите текст</label>
            <textarea
                className="post__textarea"
                name="articlesText"
                type="text"
                placeholder="Введите сообщение"
                onChange={uploadArticlesText}
            ></textarea>

            <input
                className="post__input"
                name="articlesImage"
                type="file"
                onChange={uploadImage} />

            <button className="post__btn" onClick={sendFile}>Отправить</button>

        </form>
    )
}

AdminArticlesForm.propTypes = {}

export default AdminArticlesForm
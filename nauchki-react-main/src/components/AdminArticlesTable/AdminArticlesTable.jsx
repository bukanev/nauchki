import { Delete, Edit } from '@material-ui/icons';
import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import { getPostThunk, getTagsThunk, removePostThunk } from '../../store/posts/actions';
import { selectPosts } from '../../store/posts/selectors';
import "./adminArticlesTable.scss";

export const AdminArticlesTable = () => {
    const dispatch = useDispatch();

    const articles = useSelector(selectPosts);

    const [currentTag, setCurrentTag] = useState("");
    const [isLoading, setIsLoading] = useState(false);

    // удалить через event? 
    const deleteArticle = (event) => {
        event.preventDefault();

        //получаем ссылку на текущий зарегистрированный объект, на котором обрабатывается событие, с
        //помощью event.currentTarget.id

        dispatch(removePostThunk(event.currentTarget.id))
        console.log("del", event.currentTarget.id)
    }

    useEffect(() => {
        (async () => {
            setIsLoading(true);
            await dispatch(getPostThunk());
            await dispatch(getTagsThunk());
            setIsLoading(false);
        })();
    }, []);

    useEffect(() => {
        (async () => {
            setIsLoading(true);
            await dispatch(getPostThunk(currentTag.tag));
            setIsLoading(false);
        })();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [currentTag]);

    return (
        <div className='article'>
            <div className='article__header'>
                <h3>Все статьи</h3>
            </div>
            <div className='article__content'>
                <div className='article__panel' >
                    {/* <input type="search" /> */}
                    <Link to={`add`}>Добавить статью</Link>
                </div>
                <table className='article__table table'>
                    <thead>
                        <tr>
                            <td>id</td>
                            <td>название</td>
                            <td>описание</td>
                            <td>tag</td>
                            <td>ред</td>
                            <td>уд</td>
                        </tr>
                    </thead>
                    <tbody>
                        {articles.map((article) => (
                            <tr key={article.id}>
                                <td>{article.id}</td>
                                <td>{article.title}</td>
                                <td>{article.subtitle}</td>
                                <td>{article.tag}</td>
                                <td>
                                    <Link
                                        className='article__table-button article__table-button_edit'
                                        to={`articles/edit/${article.id}`}>
                                        <Edit />
                                    </Link>
                                </td>
                                <td>
                                    <button
                                        id={article.id}
                                        className='article__table-button article__table-button_delete'
                                        onClick={deleteArticle}
                                    >
                                        <Delete />
                                    </button>

                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>

        </div >
    )
}
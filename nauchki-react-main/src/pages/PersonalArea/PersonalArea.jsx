import React, { useEffect, useState, useCallback } from 'react';
import { useSelector } from 'react-redux';
import { useDispatch } from 'react-redux';
import { Route, useHistory } from 'react-router';
import axios from 'axios';
import { AddChildrenForm } from '../../components/AddChildrenForm/AddChildrenForm';
import { ChildCard } from '../../components/ChildCart/ChildCart';
import childPlaceholder from '../../img/childCardPlaceholder.jpg';
import { selectUserData } from '../../store/user/selectors';
import { selectUserChildrenData } from '../../store/userChildren/selectors';
import { getUserChildrenThunk } from '../../store/userChildren/actions';
import { toggleAuth } from '../../store/user/actions';

export const PersonalArea = () => {
  const dispatch = useDispatch();
  const [visibleForm, setVisibleForm] = useState(false);
  const [img, setImg] = useState(null);
  const [avatar, setAvatar] = useState(null);
  const user = useSelector(selectUserData);
  const children = useSelector(selectUserChildrenData)

  let history = useHistory();
  const exitHandler = () => {
    dispatch(toggleAuth(false));
    history.push('/');
  };

  const toggleVisibleForm = () => {
    setVisibleForm(!visibleForm);
  };

  const getUserChildren = () => {
    dispatch(getUserChildrenThunk(user.id))
  };

  //IMG
  const sendFile = useCallback(async () => {
    try {
      const date = new FormData();
      date.append('file', img);
      await axios
        .post(`http://89.108.88.2:8080/${user.id}`, date, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        })

        .then((res) => setAvatar(res.data));
    } catch (error) {
      console.log(error);
    }
  }, [img]);

  useEffect(() => {
    getUserChildren();
  }, []);

  return (
    <div className="personalArea">
      <h1 className="personalArea__title">Мой кабинет</h1>

      <div className="personalArea__parent">
        <button className="personalArea__button personalArea__button-exit" onClick={exitHandler}>
          Выйти
        </button>
        <div className="personalArea__avatar">
          {user.img_path ? (
            <img
              className="personalArea__avatar-img"
              src={`${avatar || user.img_path}`}
              alt="avatar"
            />
          ) : (
            <img
              className="personalArea__avatar-img"
              src={`${avatar || childPlaceholder}`}
              alt="avatar"
            />
          )}
        </div>
        <div className="upload-file">
          <div className="upload-file__wrapper">
            <input
              type="file"
              name="files"
              id="upload-file__input_1"
              className="upload-file__input"
              accept=".jpg, .jpeg, .png, .gif, .bmp, .doc, .docx, .xls, .xlsx, .txt, .tar, .zip, .7z, .7zip"
              multiple
            />
            <label onClick={sendFile} className=" personalArea__button" htmlFor="upload-file__input_1">
              <span className="upload-file__text">Прикрепить файл</span>
            </label>
          </div>
        </div>
+        {/* <div className="personalArea__parent_name">{user.username}</div>
         <div className="personalArea__parent_email">Email: {user.email}</div>
        <div className="personalArea__parent_login">login:{user.login}</div>
        <div className="personalArea__parent_number">number:{user.number}</div> */}

        <div className="personalArea__family">
          <div className="personalArea__list">
            <div className="personalArea__avatar-family">
              {user.img_path ? (
                <img
                  className="personalArea__avatar-img"
                  src={`${avatar || user.img_path}`}
                  alt="avatar"
                />
              ) : (
                <img
                  className="personalArea__avatar-img"
                  src={`${avatar || childPlaceholder}`}
                  alt="avatar"
                />
              )}
            </div>
            <div className="personalArea__avatar-family">
              {user.img_path ? (
                <img
                  className="personalArea__avatar-img"
                  src={`${avatar || user.img_path}`}
                  alt="avatar"
                />
              ) : (
                <img
                  className="personalArea__avatar-img"
                  src={`${avatar || childPlaceholder}`}
                  alt="avatar"
                />
              )}
            </div>
            <button className=" circle  personalArea__button personalArea__button-circle"></button>
          </div>

          <p className="personalArea__family-text">
            Добавьте родственников, чтобы они следили за тем, как развивается ваш малыш
          </p>
        </div>
      </div>
      <div className="personalArea__main">
        <div className="personalArea__main-wrapper">
          <h1 className="personalArea__main-title">Мои дети </h1>
          {!visibleForm && (
            <button
              onClick={toggleVisibleForm}
              className="circle personalArea__button personalArea__button-circle"></button>
          )}
        </div>

        {visibleForm && (
          <AddChildrenForm
            userId={user.id}
            visibleForm={visibleForm}
            setVisibleForm={setVisibleForm}
            getUserChildren={getUserChildren}
          />
        )}

        <ul className="personalArea__children-container ">
          {children && children.map((child) => <ChildCard key={child.id} child={child} />)}
        </ul>

        {<Route exact path="/personalArea/:id" render={(props) => <ChildCard {...props} />} />}
      </div>
    </div >
  );
};

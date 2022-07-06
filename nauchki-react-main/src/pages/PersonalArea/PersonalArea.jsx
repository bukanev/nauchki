import React, { useEffect, useState, useCallback } from 'react';
import { useSelector, useDispatch, shallowEqual } from 'react-redux';
// eslint-disable-next-line no-unused-vars
import axios from 'axios';
import { AddChildrenForm } from '../../components/AddChildrenForm/AddChildrenForm';
import { ChildCard } from '../../components/ChildCart/ChildCart';
import childPlaceholder from '../../img/childCardPlaceholder.jpg';
import { selectUserData } from '../../store/user/selectors';
import { selectUserChildrenData } from '../../store/userChildren/selectors';
import { getUserChildrenThunk } from '../../store/userChildren/actions';
import { addImagesThunk, logout } from '../../store/user/actions';
import { NavLink, useNavigate } from 'react-router-dom';

export const PersonalArea = () => {
  const dispatch = useDispatch();
  const [visibleForm, setVisibleForm] = useState(false);
  const [img, setImg] = useState(null);
  // eslint-disable-next-line no-unused-vars
  const [avatar, setAvatar] = useState(null);
  const user = useSelector(selectUserData, shallowEqual);
  const children = useSelector(selectUserChildrenData)

  let history = useNavigate();
  const exitHandler = () => {
    dispatch(logout());
    history('/');
  };

  const toggleVisibleForm = () => {
    setVisibleForm(!visibleForm);
  };


  const getUserChildren = (userId) => {
    dispatch(getUserChildrenThunk(userId))
  };

  const uploadImage = (event) => {
    event.preventDefault();

    if (event.target.files[0]) {
      setImg(event.target.files[0])
    }
  }

  const sendFile = () => {
    const dataImg = new FormData();
    dataImg.append('file', img);

    dispatch(addImagesThunk(dataImg));
  }

  useEffect(() => {
    user?.id && getUserChildren(user.id);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [user]);

  return (
    <div className="personalArea">
      <h1 className="personalArea__title">Мой кабинет</h1>

      <div className="personalArea__parent">
        <button className="personalArea__button personalArea__button-exit" onClick={exitHandler}>
          Выйти
        </button>
        <div className="personalArea__avatar">
          <div
            className="personalArea__avatar-img"
            style={{ backgroundImage: `url(${user?.images[8]?.externalPath || childPlaceholder})` }}
          ></div>
        </div>
        <form
          onSubmit={(e) => {
            e.preventDefault();
          }}
          className="upload-file">
          <div className="upload-file__wrapper">
            <input
              type="file"
              name="files"
              id="upload-file__input_1"
              onChange={uploadImage}
              // className="upload-file__input"
              accept=".jpg, .jpeg, .png, .gif, .bmp, .doc, .docx, .xls, .xlsx, .txt, .tar, .zip, .7z, .7zip"
              multiple
            />
            <button onClick={sendFile}
              className=" personalArea__button"
            // htmlFor="upload-file__input_1"
            >
              <span className="upload-file__text">Прикрепить файл</span>
            </button>
          </div>
        </form>


        <div className="personalArea__family">
          <div className="personalArea__list">
            <div
              className="personalArea__avatar-family"
              style={{ backgroundImage: `url(${user?.images[8]?.externalPath || childPlaceholder})` }}
            >
            </div>
            <div
              className="personalArea__avatar-family"
              style={{ backgroundImage: `url(${user?.images[-1]?.externalPath || childPlaceholder})` }}
            >
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

        <div className="personalArea__children-container ">
          {children?.map((child) =>
            <ChildCard key={child.id} child={child} />
          )}
        </div>

        <NavLink to="/personalArea/:id" render={<ChildCard />}></NavLink>
      </div>
    </div >
  );
};

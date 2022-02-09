import React, { useEffect, useState, useCallback } from "react";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { getUserDataAC, toggleAuthAC } from "../../store/userReducer";
import { Route, useHistory } from "react-router";
import axios from "axios";
import { AddChildrenForm } from "./AddChildrenForm";
import { getChildrenAC } from "../../store/childrenReducer";
import { ChildCard } from "./ChildCart";
import childPlaceholder from "../../img/childCardPlaceholder.jpg";

export const PersonalArea = () => {
  const user = useSelector((state) => state.user.userData);
  const children = useSelector((state) => state.children.children);
  const [visibleForm, setVisibleForm] = useState(false);
  const dispatch = useDispatch();
  let history = useHistory();
  const exitHandler = () => {
    dispatch(toggleAuthAC(false));
    history.push("/");
  };
  // TODO: Перенести в санки получение детей
  const getChildrenData = (userData) => {
    dispatch(getChildrenAC(userData));
  };
  const getUserChildren = () => {
    axios
      .get(`https://nauchki.herokuapp.com/getchildren/${user.id}`, {
        withCredentials: true,
      })
      .then((res) => {
        getChildrenData(res.data);
      });
  };
  useEffect(() => {
    getUserChildren();
  }, []);
  //IMG
  const [img, setImg] = useState(null);
  const [avatar, setAvatar] = useState(null);

  const sendFile = useCallback(async () => {
    try {
      const date = new FormData();
      date.append("file", img);
      await axios
        .post(`https://nauchki.herokuapp.com/addimage/${user.id}`, date, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        })

        .then((res) => setAvatar(res.data));
    } catch (error) {
      console.log(error);
    }
  }, [img]);

  console.log(children);
  return (
    <div className="_wrapper">
      <div className="personalArea_container">
        <h1 className="personalArea__title">Мой кабинет</h1>
        <div className="personalArea__parent">
          <button className="personalArea__parent_exit" onClick={exitHandler}>
            Выйти
          </button>
          <div>
            {user.img_path ? (
              <img
                className="personalArea__parent_logo"
                src={`${avatar || user.img_path}`}
                alt="avatar"
              />
            ) : (
              <img
                className="personalArea__parent_logo"
                src={`${avatar || childPlaceholder}`}
                alt="avatar"
              />
            )}
          </div>
          <input onChange={(e) => setImg(e.target.files[0])} type="file" />
          <button onClick={sendFile} className="personalArea__parent_button">
            Добавить фото
          </button>
          <div className="personalArea__parent_name">{user.username}</div>
          <div className="personalArea__parent_email">Email: {user.email}</div>
          <div className="personalArea__parent_login">login:{user.login}</div>
          <div className="personalArea__parent_number">
            number:{user.number}
          </div>
        </div>
        <div className="personalArea__main">
          <h1 className="personalArea__title-children">
            Мои дети{" "}
            {!visibleForm && (
              <button
                onClick={() => setVisibleForm(!visibleForm)}
                className="circle"
              ></button>
            )}
            {visibleForm && (
              <AddChildrenForm
                userId={user.id}
                visibleForm={visibleForm}
                setVisibleForm={setVisibleForm}
                getUserChildren={getUserChildren}
              />
            )}
          </h1>

          <ul className="personalArea__children-container ">
            {children &&
              children.map((child) => (
                <ChildCard key={child.id} child={child} />
              ))}
          </ul>

          {
            <Route
              exact
              path="/personalArea/:id"
              render={(props) => <ChildCard {...props} />}
            />
          }
        </div>
      </div>
      <br />
      <br />
    </div>
  );
};

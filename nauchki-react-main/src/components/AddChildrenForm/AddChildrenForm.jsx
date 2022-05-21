import axios from 'axios';
import React, { useState } from 'react';
import './addChildrenForm.scss';
import { DateOfBirthInput } from '../../UI/DateOfBirthInput';
import { PlussBtn } from '../../UI/PlussBtn';

export const AddChildrenForm = ({ userId, visibleForm, setVisibleForm, getUserChildren }) => {
  const [name, setName] = useState('');
  const [gender, setGender] = useState('');
  const [dateOfBirth, setDateOfBirth] = useState(null);

  const toggleVisibleForm = () => {
    setVisibleForm(!visibleForm);
  };

  const addUserChildren = () => {
    axios
      .post(
        `http://89.108.88.2:8080/children/${userId}`,
        { name: name, gender: gender, dateOfBirth: dateOfBirth },
        { withCredentials: true },
      )
      .then((res) => {
        console.log(res);
        getUserChildren();
        setVisibleForm(false);
      });
  };

  const onChangeValue = (event) => {
    setGender(event.target.value);
  };

  return (
    <div className="personalArea-form">
      <div className="personalArea-form-wrapper">
        <h1 className="personalArea-form__title">Добавить ребенка в личный кабинет</h1>
        <PlussBtn onClick={toggleVisibleForm} className="circleCross " />
      </div>

      <div className="personalArea-form__main">
        <form
          onSubmit={(e) => {
            e.preventDefault();
          }}>
          <p className="personalArea-form__ptitle">Имя ребенка</p>

          <input
            className="personalArea-form__name"
            placeholder="name"
            onChange={(e) => setName(e.target.value)}
          />
          <br />
          <div className="personalArea-form__radio" onChange={onChangeValue}>
            <input type="radio" value="Муж" name="gender" />
            муж.
            <input type="radio" value="Жен" name="gender" /> жен.
          </div>
          <br />
          <p className="personalArea-form__ptitle">Дата рождения</p>

          <DateOfBirthInput dateOfBirth={dateOfBirth} setDateOfBirth={setDateOfBirth} value="" />

          <button className="personalArea-form__btn" onClick={addUserChildren} type="submit">
            Добавить
          </button>
        </form>
      </div>
    </div>
  );
};

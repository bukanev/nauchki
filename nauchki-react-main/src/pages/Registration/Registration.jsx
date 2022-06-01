import React, { useState } from "react";
import { DataProvider } from "./DataContext";
import { Controller, useForm } from "react-hook-form";
import { PrimaryButton } from "../../UI/PrimaryButton";
import { Form } from "../../UI/Form";
import { Input } from "../../UI/Input";
import { useHistory } from "react-router";
import { yupResolver } from "@hookform/resolvers/yup";
import * as yup from "yup";
import { NavLink } from "react-router-dom";
import { RegistartionAPI } from "../../api/api";
import Checkbox from "@material-ui/core/Checkbox";
import { MainContainer } from "../../UI/MainContainer";
import { LoaderSvg } from "../../UI/LoaderSvg";
import "./phone.scss"
import PhoneInput from 'react-phone-input-2';
import 'react-phone-input-2/lib/style.css';

const schema = yup.object({
  login: yup
    .string()
    .required("Логин - обязательное поле")
    .matches(/^([^0-9]*)$/, "login не должен сдержать цифры"),
  password: yup
    .string()
    .required("Пароль - обязательное поле")
    .matches(
      /(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{6,}/g,
      "Пароль должен содержать не менее 6 символов, включать в себя цифры, латиницу, строчные и прописные символы"
    ),
  passwordRecovery: yup
    .string()
    .oneOf([yup.ref('password'), null], 'Пароли не совпадают'),
  email: yup.string().matches(/.+@.+\..+/i, "Почта должна содержать @ \n (пример example@mail.ru)"),
});

export const Registration = () => {
  const [checkbox, setCheckbox] = useState(false);
  const [isLoading, setIsLoading] = useState(false);

  let history = useHistory();

  const {
    register,
    control,
    formState: { errors },
    handleSubmit,
  } = useForm({
    mode: "onBlur",
    resolver: yupResolver(schema),
  });

  const onSubmit = (data) => {
    console.log(data)
    setIsLoading(true);
    RegistartionAPI.registartion(
      data.email,
      data.login,
      "+" + data.number,
      data.password,
      data.username
    )
      .then((res) => {
        history.push("/login");
      })
      .catch((err) => {
        if (err.response) {
          console.log("client received an error response (5xx, 4xx)");
        } else if (err.request) {
          console.log("client never received a response, or request never left");
        } else {
          console.log("anything else");
        }
      });
  };

  return (
    <DataProvider>
      <MainContainer>
        <Form onSubmit={handleSubmit(onSubmit)}>
          <h2 className="login_title">Регистрация</h2>

          <Input
            {...register("login")}
            id="login"
            type="text"
            placeholder="Введите логин"
            name="login"
            autoComplete="on"
            error={!!errors.login}
          />
          <p className="errorText">{errors?.login?.message}</p>

          <Input
            {...register("password", { required: true })}
            id="password"
            type="password"
            name="password"
            placeholder="Введите пароль"
            autoComplete="on"
            error={!!errors.password}
          />
          <p className="errorText">{errors?.password?.message}</p>
          <Input
            {...register("passwordRecovery", { required: true })}
            id="passwordRecovery"
            type="password"
            name="passwordRecovery"
            placeholder="Повторите пароль"
            autoComplete="on"
            error={!!errors.passwordRecovery}
          />
          <p className="errorText">{errors?.passwordRecovery?.message}</p>

          <Controller
            control={control}
            name="number"
            render={({ field: { onChange } }) => (
              <PhoneInput
                country={'ru'}
                onChange={onChange}
                containerClass="phone-container"
                inputClass="phone-input"
                buttonClass="phone-btn"
              />
            )}
          />

          <p className="errorText">{errors?.number?.message}</p>

          <Input
            {...register("email")}
            id="email"
            type="text"
            name="email"
            placeholder="Введите вашу почту"
            autoComplete="on"
          />
          <p className="errorText">{errors?.email?.message}</p>

          <div className="registartion-checkbox">
            <Checkbox
              checked={checkbox}
              onChange={() => {
                setCheckbox(!checkbox);
              }}
              inputProps={{ "aria-label": "primary checkbox" }}
            />
            Согласны ли вы что-то там
          </div>

          <PrimaryButton disabled={!checkbox}>{isLoading ? <LoaderSvg /> : <p>Отправить</p>}</PrimaryButton>
          <div className="routerLinkAuth">
            <NavLink className="routerLinkAuth-text" to="/login">
              Вход
            </NavLink>
          </div>
        </Form>
      </MainContainer>
    </DataProvider>
  );
};

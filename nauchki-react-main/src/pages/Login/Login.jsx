import { Input } from "../../UI/Input";
import React, { useState } from "react";
import { useForm } from "react-hook-form";
import { Form } from "../../UI/Form";
import { MainContainer } from "../../UI/MainContainer";
import { PrimaryButton } from "../../UI/PrimaryButton";
import { LogDataProvider } from "./DataContextLog";
import { yupResolver } from "@hookform/resolvers/yup";
import * as yup from "yup";
import { NavLink } from "react-router-dom";
import { useDispatch } from "react-redux";
import { LoaderSvg } from "../../UI/LoaderSvg";
import { asyncApiCall } from "../../store/user/actions";

const schema = yup.object({
  email: yup.string().email().required(),
  password: yup.string().required(),
});

export const Login = () => {
  const [isLoading, setIsLoading] = useState(false);
  const dispatch = useDispatch();

  const {
    register,
    formState: { errors },
    handleSubmit,
  } = useForm({
    mode: "onBlur",
    resolver: yupResolver(schema),
  });

  const onSubmit = (data) => {
    setIsLoading(true);
    dispatch(asyncApiCall(data.email, data.password));
  };

  return (
    <LogDataProvider>
      <MainContainer>
        <Form onSubmit={handleSubmit(onSubmit)}>
          <h1 className="login_title">Вход</h1>
          <Input
            {...register('email')}
            id="email"
            type="email"
            name="email"
            placeholder="email"
            autoComplete="on"
            error={!!errors.email}
          />
          <p className="errorText">{errors?.email?.message}</p>
          <Input
            {...register("password")}
            id="password"
            type="password"
            name="password"
            placeholder="password"
            autoComplete="on"
            error={!!errors.password}
          />
          <p className="errorText">{errors?.password?.message}</p>

          <PrimaryButton>{isLoading ? <LoaderSvg /> : <p>Войти</p>}</PrimaryButton>
          <div className="routerLinkAuth">
            <NavLink className="routerLinkAuth-text routerLinkAuth-text_block" to="/registration">
              Регистрация
            </NavLink>

            <NavLink className="routerLinkAuth-text routerLinkAuth-text_block" to="/recoverypass">
              Забыли пароль
            </NavLink>
          </div>
        </Form>
      </MainContainer>
    </LogDataProvider>
  );
};

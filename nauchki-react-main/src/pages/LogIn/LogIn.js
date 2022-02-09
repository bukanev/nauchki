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
import { asyncApiCall } from "../../asyncActions/getAuthUserThunk";
import { LoaderSvg } from "../../UI/LoaderSvg";


const schema = yup.object({
  login: yup.string().required(),
  password: yup.string().required(),
});

export const LogIn = () => {
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
    dispatch(asyncApiCall(data.login, data.password));
  };

  return (
    <LogDataProvider>
      
      <MainContainer>
        <Form onSubmit={handleSubmit(onSubmit)}>
          <h1 className="login_title">Вход</h1>
          <Input
            {...register("login")}
            id="login"
            type="text"
            name="login"
            placeholder="login"
            autoComplete="on"
            error={!!errors.login}
          />
          <p className="errorText">{errors?.login?.message}</p>
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
            <NavLink className="routerLinkAuth-text" to="/registration">
              Регистарция
            </NavLink>
          </div>
        </Form>
      </MainContainer>
    </LogDataProvider>
  );
};

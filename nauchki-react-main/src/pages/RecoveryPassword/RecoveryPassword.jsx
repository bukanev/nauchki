import React from 'react';
import { useForm } from 'react-hook-form';
import { LogDataProvider } from '../Login/DataContextLog';
import { Input } from '../../UI/Input';
import { PrimaryButton } from '../../UI/PrimaryButton';
import * as yup from "yup";
import { yupResolver } from "@hookform/resolvers/yup";
import { Form } from '../../UI/Form';
import { useDispatch, useSelector } from 'react-redux';
import { getRecoveryPassData } from '../../store/recoveryPassReducer';
import { getRecoveryPassThunk } from '../../asyncActions/getRecoveryPassThunk';


const schema = yup.object({
  email: yup
    .string()
    .matches(/.+@.+\..+/i, "Почта должна содержать @ \n (пример example@mail.ru)"),
});

export const RecoveryPassword = () => {
  const dispatch = useDispatch();
  const { data, error } = useSelector(getRecoveryPassData);
  const {
    register,
    formState: { errors },
    handleSubmit,
  } = useForm({
    mode: "onBlur",
    resolver: yupResolver(schema),
  });

  const onSubmit = async (data) => {
    dispatch(await getRecoveryPassThunk(data));
  };

  console.log(data, error?.request?.status);

  return (
    <LogDataProvider>
      <Form onSubmit={handleSubmit(onSubmit)} className="reset">
        <h1 className="login_title">Восстановление пароля</h1>
        <Input
          {...register("email")}
          id="email"
          type="email"
          name="email"
          placeholder="Введите вашу почту"
          autoComplete="on"
          error={!!errors.email}
        />
        <p className="errorText">{errors?.email?.message}</p>

        <PrimaryButton>Отправить</PrimaryButton>
      </Form>
    </LogDataProvider>
  );
};

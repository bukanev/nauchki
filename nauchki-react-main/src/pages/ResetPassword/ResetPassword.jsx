import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useForm } from 'react-hook-form';
import './resetPassword.scss';
import { LogDataProvider } from '../Login/DataContextLog';
import { Input } from '../../UI/Input';
import { PrimaryButton } from '../../UI/PrimaryButton';
import { Form } from '../../UI/Form';
import * as yup from "yup";
import { yupResolver } from "@hookform/resolvers/yup";
import { ModalWindow } from '../../components/ModalWindow/ModalWindow';
import { selectResetPassData } from '../../store/resetPass/selectors';
import { getResetPassThunk } from '../../store/resetPass/actions';

const schema = yup.object({
  resetPasswordCode: yup
    .string()
    .required("Код восстановления - обязательное поле")
    .matches(/^[0-9]+$/, "Код восстановления должен состоять только из цифр")
    .min(5, 'Код восстановления должен иметь 5 цифр')
    .max(5, 'Код восстановления должен иметь 5 цифр'),
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
});

export const ResetPassword = () => {
  const dispatch = useDispatch();
  const { data, error } = useSelector(selectResetPassData);
  const [showModalClick, setShowModalClick] = useState(false);
  const {
    register,
    formState: { errors },
    handleSubmit,
  } = useForm({
    mode: "onBlur",
    resolver: yupResolver(schema),
  });

  const accessRequest = data?.request?.status === 200;

  const onSubmit = async (resetPasswordCode, password) => {
    dispatch(getResetPassThunk(resetPasswordCode, password));
  }

  const toggleShowModalClick = () => {
    setShowModalClick(!showModalClick)
  }

  useEffect(() => {
    if (accessRequest) {
      toggleShowModalClick();
    }
  }, [data]);

  return (
    <LogDataProvider>
      <Form className="reset" onSubmit={handleSubmit(onSubmit)}>
        <h1 className="login_title">Восстановление пароля</h1>
        <Input
          {...register('resetPasswordCode')}
          id="resetPasswordCode"
          type="number"
          name="resetPasswordCode"
          placeholder="Введите код"
          autoComplete="on"
          error={!!errors.resetPasswordCode}
        />
        <p className="errorText">{errors?.resetPasswordCode?.message} </p>
        <Input
          {...register('password', { required: true })}
          id="password"
          type="password"
          name="password"
          placeholder="Введите пароль"
          autoComplete="on"
          error={!!errors.password}
        />
        <p className="errorText">{errors?.password?.message} </p>
        <Input
          {...register('passwordRecovery', { required: true })}
          id="passwordRecovery"
          type="password"
          name="passwordRecovery"
          placeholder="Повторите пароль"
          autoComplete="on"
          error={!!errors.passwordRecovery}
        />
        <p className="errorText">{errors?.passwordRecovery?.message} </p>
        {error?.request?.status === 304 ? 'Неверный код' : ''}
        <PrimaryButton>Войти</PrimaryButton>
      </Form>

      {accessRequest &&
        <ModalWindow
          showModalClick={showModalClick}
          toggleShowModalClick={toggleShowModalClick}
          redirectPath={`/login`}
        > пароль изменен </ModalWindow>
      }
    </LogDataProvider>
  );
};

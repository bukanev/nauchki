import React from 'react';
import './resetPassword.scss';
import { LogDataProvider } from '../Login/DataContextLog';
// import { MainContainer } from '../../UI/MainContainer';
// import { Form } from '../../UI/Form';
import { Input } from '../../UI/Input';
import { PrimaryButton } from '../../UI/PrimaryButton';

export const ResetPassword = () => {
  return (
    <LogDataProvider>
      <form className="reset">
        <h1 className="login_title">Восстановление пароля</h1>
        <Input
          id="password"
          type="password"
          name="password"
          placeholder="Введите пароль"
          autoComplete="on"
        />

        <Input
          id="repeatPassword"
          type="repeatPassword"
          name="repeatPassword"
          placeholder="Повторите пароль"
          autoComplete="on"
        />
        <PrimaryButton>Войти</PrimaryButton>
      </form>
    </LogDataProvider>
  );
};

import React from 'react';
import { LogDataProvider } from '../Login/DataContextLog';
import { Input } from '../../UI/Input';
import { PrimaryButton } from '../../UI/PrimaryButton';

export const RecoveryPassword = () => {
  return (
    <LogDataProvider>
      <form className="reset">
        <h1 className="login_title">Восстановление пароля</h1>
        <Input
          id="email"
          type="email"
          name="email"
          placeholder="Введите вашу почту"
          autoComplete="on"
        />

        <PrimaryButton>Отправить</PrimaryButton>
      </form>
    </LogDataProvider>
  );
};

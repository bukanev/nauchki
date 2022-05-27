import React, { useEffect, useState } from 'react';
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
import { ModalWindow } from '../../components/ModalWindow/ModalWindow';

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
  const [showModalClick, setShowModalClick] = useState(false);

  const onSubmit = async (email) => {
    dispatch(await getRecoveryPassThunk(email));
  };

  const toggleShowModalClick = () => {
    setShowModalClick(!showModalClick)
  }
  useEffect(() => {
    if (data?.request?.status === 200) {
      toggleShowModalClick();
    }
  }, [data])

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
        <p className="errorText">
          {error?.request?.status === 304 ?
            'Почта не зарегистрирована'
            : ''}
        </p>

        <PrimaryButton>Отправить</PrimaryButton>
      </Form>
      {data?.request?.status === 200 ?
        <ModalWindow
          showModalClick={showModalClick}
          toggleShowModalClick={toggleShowModalClick}
        >
          Письмо на почту отправлено
        </ModalWindow> : ''
      }


    </LogDataProvider>
  );
};

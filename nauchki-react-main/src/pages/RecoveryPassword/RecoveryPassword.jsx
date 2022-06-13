import React, { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import { LogDataProvider } from '../Login/DataContextLog';
import { Input } from '../../UI/Input';
import { PrimaryButton } from '../../UI/PrimaryButton';
import * as yup from "yup";
import { yupResolver } from "@hookform/resolvers/yup";
import { Form } from '../../UI/Form';
import { useDispatch, useSelector } from 'react-redux';
import { selectRecoveryPassData } from '../../store/recoveryPass/selectors';
import { ModalWindow } from '../../components/ModalWindow/ModalWindow';
import { getRecoveryPassThunk } from '../../store/recoveryPass/actions';

const schema = yup.object({
  email: yup
    .string()
    .matches(/.+@.+\..+/i, "Почта должна содержать @ \n (пример example@mail.ru)"),
});

export const RecoveryPassword = () => {
  const dispatch = useDispatch();
  const { data, error } = useSelector(selectRecoveryPassData);
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

  const onSubmit = async (email) => {
    dispatch(await getRecoveryPassThunk(email));
  };

  const toggleShowModalClick = () => {
    setShowModalClick(!showModalClick)
  }
  useEffect(() => {
    if (accessRequest) {
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
      {accessRequest &&
        <ModalWindow
          showModalClick={showModalClick}
          toggleShowModalClick={toggleShowModalClick}
          redirectPath={`/resetpass`}
        >
          Письмо на почту отправлено
        </ModalWindow>
      }
    </LogDataProvider>
  );
};

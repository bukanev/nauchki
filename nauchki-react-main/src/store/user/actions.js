import { LoginAPI, UserAPI } from '../../api/api';

export const GET_USER_DATA = 'GET_USER_DATA';
export const TOGGLE_AUTH = 'TOGGLE_AUTH';

export const getUserData = (payload) => ({
  type: GET_USER_DATA,
  payload,
});

export const toggleAuth = (payload) => ({
  type: TOGGLE_AUTH,
  payload,
});

export const asyncApiCall = (email, password) => {
  return async (dispatch) => {
    //получает токен
    await LoginAPI.auth(email, password).then((res) => {
      const token = res.data;
      localStorage.setItem('TOKEN', token);
    });

    //получает данные юзера
    await UserAPI.getAuthUser(email, password)
      .then((res) => {
        dispatch(getUserData(res.data));
        dispatch(toggleAuth(true));
      })
      .catch((err) => {
        console.log(err);
      });
  };
};

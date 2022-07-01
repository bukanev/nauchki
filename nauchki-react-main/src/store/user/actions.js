import { LoginAPI, UserAPI } from '../../api/api';

export const GET_USER_DATA = 'GET_USER_DATA';
export const TOGGLE_AUTH = 'TOGGLE_AUTH';
export const ERROR_AUTH = 'USER::ERROR_AUTH';

export const getUserData = (payload) => ({
  type: GET_USER_DATA,
  payload,
});

export const toggleAuth = (payload) => ({
  type: TOGGLE_AUTH,
  payload,
});

export const errorAuth = (payload) => ({
  type: ERROR_AUTH,
  payload,
});

export const asyncApiCall = (email, password) => async (dispatch) => {
  await LoginAPI.auth(email, password)
    .then((res) => {
      localStorage.setItem('TOKEN', res.data);
      dispatch(toggleAuth(true));
    })
    .catch((err) => {
      dispatch(errorAuth(err));
    });

  await UserAPI.getAuthUser(email, password)
    .then((res) => {
      if (localStorage.getItem('TOKEN')) {
        dispatch(getUserData(res.data));
        dispatch(toggleAuth(true));
      }
    })
    .catch((err) => {
      dispatch(errorAuth(err));
    });
};

export const logout = () => (dispatch) => {
  localStorage.removeItem('TOKEN', 'persist:root');
  dispatch(toggleAuth(false));
};

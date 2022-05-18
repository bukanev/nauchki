import { LoginAPI, UserAPI } from '../api/api';
import { getUserDataAC, toggleAuthAC } from '../store/userReducer';

export const asyncApiCall = (email, password) => {
  return async (dispatch) => {
    await LoginAPI.auth(email, password).then((res) => {
      const token = res.data;
      localStorage.setItem('TOKEN', token);
    });

    await UserAPI.getAuthUser(email, password)
      .then((res) => {
        dispatch(getUserDataAC(res.data));
        dispatch(toggleAuthAC(true));
      })
      .catch((err) => {
        if (err.response) {
          alert('client received an error response (5xx, 4xx)'); // обработать для UI
        } else if (err.request) {
          alert('client never received a response, or request never left'); // обработать для UI
        } else {
          alert('anything else'); // обработать для UI
        }
      });
  };
};

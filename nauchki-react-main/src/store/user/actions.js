import { LoginAPI, UserAPI } from '../../api/api';

export const GET_USER_DATA = 'USER::GET_USER_DATA';
export const TOGGLE_AUTH = 'USER::TOGGLE_AUTH';
export const ERROR_AUTH = 'USER::ERROR_AUTH';
export const ADD_IMAGES = 'PERSONAL_AREA::ADD_IMAGES';

export const getUserData = (payload) => ({
  type: GET_USER_DATA,
  payload,
});

export const toggleAuth = (payload) => ({
  type: TOGGLE_AUTH,
  payload,
});

export const addImage = (payload) => ({
  type: ADD_IMAGES,
  payload,
});
export const errorAuth = (payload) => ({
  type: ERROR_AUTH,
  payload,
});
export const addImages = (payload) => ({ type: ADD_IMAGES, payload });

export const asyncApiCall = (email, password) => async (dispatch) => {
  await LoginAPI.auth(email, password)
    .then((res) => {
      localStorage.setItem('TOKEN', res.data);
      dispatch(toggleAuth(true));
    })
    .catch((err) => {
      dispatch(errorAuth(err));
    });

  await UserAPI.getAuthUser()
    .then((res) => {
      if (localStorage.getItem('TOKEN')) {
        dispatch(getUserData(res.data));
        dispatch(toggleAuth(true));
      }
    })
    .catch((err) => {
      console.log(err);
    });
};

export const getUserDataThunk = () => (dispatch) => {
  try {
    const response = UserAPI.getAuthUser();

    if (response?.request?.status === 200) {
      dispatch(getUserData(response.data));
      dispatch(toggleAuth(true));
    }
  } catch (error) {
    console.log(error);
  }
};

export const addImagesThunk = (data) => (dispatch) => {
  try {
    // const response = UserAPI.postAddImg(data);
    console.log(data);
    const addimg = { id: 1, src: 'dsadasd' };
    dispatch(addImages(addimg));
  } catch (error) {
    console.log(error);
  }
};

export const logout = () => (dispatch) => {
  localStorage.removeItem('TOKEN', 'persist:root');
  dispatch(toggleAuth(false));
};

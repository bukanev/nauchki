export const SET_RESET_PASS_LOADING = 'RESET_PASS::SET_RESET_PASS_LOADING';
export const SET_RESET_PASS_DATA = 'RESET_PASS::SET_RESET_PASS_DATA';
export const SET_RESET_PASS_ERROR = 'RESET_PASS::SET_RESET_PASS_ERROR';

export const setResetPassLoading = () => ({
  type: SET_RESET_PASS_LOADING,
});

export const setResetPassData = (data) => ({
  type: SET_RESET_PASS_DATA,
  payload: data,
});

export const setResetPassError = (error) => ({
  type: SET_RESET_PASS_ERROR,
  payload: error,
});

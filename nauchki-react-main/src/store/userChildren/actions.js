import { UserChildrenAPI } from '../../api/api';

export const SET_USER_CHILDREN_LOADING = 'USER_CHILDREN::SET_USER_CHILDREN_LOADING';
export const SET_USER_CHILDREN_DATA = 'USER_CHILDREN::SET_USER_CHILDREN_DATA';
export const SET_USER_CHILDREN_ERROR = 'USER_CHILDREN::SET_USER_CHILDREN_ERROR';

const setUserChildrenLoading = () => ({
  type: SET_USER_CHILDREN_LOADING,
});
const setUserChildrenData = (data) => ({
  type: SET_USER_CHILDREN_DATA,
  payload: data,
});
const setUserChildrenError = (error) => ({
  type: SET_USER_CHILDREN_ERROR,
  payload: error,
});

export const getUserChildrenThunk = (userId) => async (dispatch) => {
  try {
    dispatch(setUserChildrenLoading());
    await UserChildrenAPI.getUserChildren(userId).then((res) => {
      dispatch(setUserChildrenData(res.data));
    });
  } catch (error) {
    dispatch(setUserChildrenError(error));
    console.log(error);
  }
};

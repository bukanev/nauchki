import { UserChildrenAPI } from "../../api/api";

export const SET_USER_CHILDREN_LOADING =
  "USER_CHILDREN::SET_USER_CHILDREN_LOADING";
export const SET_USER_CHILDREN_DATA = "USER_CHILDREN::SET_USER_CHILDREN_DATA";
export const SET_USER_CHILDREN_ERROR = "USER_CHILDREN::SET_USER_CHILDREN_ERROR";
export const SET_IMG_CHILDREN = "USER_CHILDREN::SET_IMG_CHILDREN";

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

export const setImg_children = () => ({
  type: SET_IMG_CHILDREN,
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

export const setImgChildrenThunk = (childrenId, formData) => (dispatch) => {
  try {
    console.log("childrenID children img", childrenId);
    console.log("formdata children img", formData);

    const data = UserChildrenAPI.addChildrenImg(childrenId, formData);
    console.log(data);
  } catch (error) {
    console.log("error children img", error);
  }
};

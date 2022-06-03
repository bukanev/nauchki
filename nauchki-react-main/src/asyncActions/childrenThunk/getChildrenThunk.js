import { ChildrenAPI } from '../../api/api';
import { getChildrenAC } from '../../store/children/actions';

export const getChildrenThunk = (userId) => {
  return async (dispatch) => {
    try {
      await ChildrenAPI.getChildren(userId).then((res) => {
        dispatch(getChildrenAC(res.data));
      });
    } catch (error) {
      console.log(error);
    }
  };
};

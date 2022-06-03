export const SET_USER_CHILDREN_LOADING = 'USER_CHILDREN::SET_USER_CHILDREN_LOADING';
export const SET_USER_CHILDREN_DATA = 'USER_CHILDREN::SET_USER_CHILDREN_DATA';
export const SET_USER_CHILDREN_ERROR = 'USER_CHILDREN::SET_USER_CHILDREN_ERROR';

export const setUserChildrenLoading = () => ({
  type: SET_USER_CHILDREN_LOADING,
});
export const setUserChildrenData = (data) => ({
  type: SET_USER_CHILDREN_DATA,
  payload: data,
});
export const setUserChildrenError = (error) => ({
  type: SET_USER_CHILDREN_ERROR,
  payload: error,
});

const initialState = {
  loading: false,
  data: null,
  error: null,
};

export const UserChildrenReducer = (state = initialState, action) => {
  switch (action.type) {
    case SET_USER_CHILDREN_LOADING:
      return { ...state, loading: true };
    case SET_USER_CHILDREN_DATA:
      return { ...state, loading: false, data: action.payload };
    case SET_USER_CHILDREN_ERROR:
      return { ...state, loading: false, data: null, error: action.payload };
    default:
      return state;
  }
};

export const selectUserChildrenData = (state) => state.withPersist.userChildren.data;

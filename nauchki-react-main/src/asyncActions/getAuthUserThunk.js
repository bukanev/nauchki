import { LoginAPI, UserAPI } from "../api/api";
import { getUserDataAC, toggleAuthAC } from "../store/userReducer";
import { Redirect } from "react-router";

export const asyncApiCall = (login, password) => {
  return async (dispatch) => {
    await LoginAPI.auth(login, password);

    await UserAPI.getAuthUser(login, password)
      .then((res) => {
        dispatch(getUserDataAC(res.data));
        dispatch(toggleAuthAC(true));
        <Redirect to="/personalArea" />;
      })
      .catch((err) => {
        if (err.response) {
          alert("client received an error response (5xx, 4xx)"); // обработать для UI
        } else if (err.request) {
          alert("client never received a response, or request never left"); // обработать для UI
        } else {
          alert("anything else"); // обработать для UI
        }
      });
  };
};

export const selectUserData = (state) => state.withPersist.user.data;
export const selectIsAuth = (state) => state.withPersist.user.isAuth;
export const selectErrorAuth = (state) => state.withPersist.user.error;

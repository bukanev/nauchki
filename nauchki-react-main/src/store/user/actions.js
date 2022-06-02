export const GET_USER_DATA = 'GET_USER_DATA';
export const TOGGLE_AUTH = 'TOGGLE_AUTH';

export const getUserDataAC = (payload) => ({
    type: GET_USER_DATA,
    payload
});
export const toggleAuthAC = (payload) => ({
    type: TOGGLE_AUTH,
    payload
});
import * as axios from "axios";

const instance = axios.create({
  withCredentials: true,
  baseURL: "https://nauchki.herokuapp.com",
});

export const UserAPI = {
  getAuthUser(login, password) {
    return instance.post(`/user`, {
      login: login,
      password: password,
    });
  },
};

export const LoginAPI = {
  auth(login, password) {
    return instance.post(`/auth`, {
      login: login,
      password: password,
    });
  },
};

export const RegistartionAPI = {
  registartion(email, login, number, password, username) {
    return instance.post(`/registration`, {
      email: email,
      login: login,
      number: number,
      password: password,
      username: username,
    });
  },
};

export const AdminAPI = {
  addPost(data) {
    return instance.post(`/post`, data);
  },
};

export const PostsAPI = {
  getPosts(tag) {
    return instance.get(`/posts${tag !== undefined ? `/${tag}` : ""}`);
  },
  getTags() {
    return instance.get(`/tags`);
  },
};

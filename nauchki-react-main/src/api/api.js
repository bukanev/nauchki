import * as axios from "axios";
import {
  Redirect
} from "react-router";


const instance = axios.create({
  withCredentials: true,
  baseURL: "https://nauchki.herokuapp.com",
});


class Api {
  static request(url, data, method, useToken = true) {
    return instance.request({
      method,
      url,
      data,
      headers: useToken ? {
        'Authorization': localStorage.getItem('TOKEN')
      } : undefined
    }).catch(e => {
    });
  }

  static post(url, data, useToken) {
    return Api.request(url, data, "POST", useToken);
  }

  static get(url, useToken) {
    return Api.request(url, "GET", useToken);
  }
}

export const UserAPI = {
  getAuthUser(login, password) {
    return Api.post(`/user`, {
      email: login,
      password: password,
    }, true);
  },
};

export const LoginAPI = {
  auth(login, password) {
    return Api.post(`/auth`, {
      email: login,
      password: password,
    }, false);
  },
};

export const RegistartionAPI = {
  registartion(email, login, number, password, username) {
    return Api.post(`/registration`, {
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
    return Api.post(`/post`, data);
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
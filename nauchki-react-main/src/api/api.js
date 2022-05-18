import * as axios from 'axios';
import { Redirect } from 'react-router';

const instance = axios.create({
  withCredentials: true,
  baseURL: 'http://89.108.88.2:8080',
});

class Api {
  static request(url, data, method, useToken = true) {
    return instance
      .request({
        method,
        url,
        data,
        headers: useToken
          ? {
              Authorization: localStorage.getItem('TOKEN'),
            }
          : undefined,
      })
      .catch((e) => {});
  }

  static post(url, data, useToken) {
    return Api.request(url, data, 'POST', useToken);
  }

  static get(url, useToken) {
    return Api.request(url, 'GET', useToken);
  }
}

export const UserAPI = {
  getAuthUser(email, password) {
    return Api.post(
      `/user`,
      {
        email: email,
        password: password,
      },
      true,
    );
  },
};

export const LoginAPI = {
  auth(email, password) {
    return Api.post(
      `/auth`,
      {
        email: email,
        password: password,
      },
      false,
    );
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
    return instance.get(`/posts${tag !== undefined ? `/${tag}` : ''}`);
  },
  getTags() {
    return instance.get(`/tags`);
  },
};

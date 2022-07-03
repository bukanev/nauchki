import * as axios from 'axios';

const instance = axios.create({
  withCredentials: true,
  baseURL: 'http://89.108.88.2:8080',
});

class Api {
  static request(url, data, method, withToken = true) {
    const headers = withToken ? { Authorization: localStorage.getItem('TOKEN') } : undefined;
    return instance.request({ method, url, data, headers });
  }

  static post(url, data, withToken) {
    return Api.request(url, data, 'POST', withToken);
  }

  static get(url, withToken) {
    return Api.request(url, 'GET', withToken);
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
      // {
      //   headers: {
      //     Authorization: 'Bearer ' + localStorage.getItem('TOKEN'),
      //   },
      // },
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
      // username: username,
    });
  },
};

export const RecoveryPassAPI = {
  recoveryPass(email) {
    return Api.post('/editpassword', {
      email: email,
    });
  },
};

export const ResetPassAPI = {
  resetPass(resetPasswordCode, password) {
    return Api.post('/editpass', {
      resetPasswordCode: resetPasswordCode,
      password: password,
    });
  },
};

export const PostsAPI = {
  getPosts(tag) {
    return instance.get(`/posts${tag !== undefined ? `/${tag}` : ''}`);
  },
  getTags() {
    return instance.get(`/tags`);
  },
  addPost(data) {
    return Api.post(
      `/post`,
      data,
      {
        headers: {
          'Content-type': 'multipart/form-data',
        },
      },
      true,
    );
  },
  deletePost(id) {
    return Api.post(`delpost/${id}`);
  },
  // editPost(id) {
  //   return Api.post(`editpost/${id}`);
  // }
};

export const UserChildrenAPI = {
  getUserChildren(userId) {
    return instance.get(
      `getchildren/${userId}`,
      {
        headers: {
          Authorization: 'Bearer ' + localStorage.getItem('TOKEN'),
        },
      },
      true,
    );
  },
  //addChildren() { instanse.post()}
};

export const SendFileChildren = {
  addChildrenImg(id, data) {
    return instance.post(`addChildrenImg/${id}`, data, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
  },
};

export const sendFileProfile = {
  addImg(id, data) {
    return instance.post(`/addimg/${id}`, data, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
  },
};

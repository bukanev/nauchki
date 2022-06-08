// с использованием персиста
import { combineReducers } from 'redux';
import { postsReducer } from './posts/reducer';
import { userReducer } from './user/reducer';
import { childrenInputReducer } from './childrenInput/reducer';
import { UserChildrenReducer } from './userChildren/reducer';

export const withPersistReducers = combineReducers({
  user: userReducer,
  posts: postsReducer,
  phase: childrenInputReducer,
  userChildren: UserChildrenReducer,
});

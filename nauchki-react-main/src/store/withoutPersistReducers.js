// без использования персиста

import { combineReducers } from 'redux';
import { recoveryPassReducer } from './recoveryPass/reducer';
import { ResetPassReducer } from './resetPass/reducer';

export const withoutPersistReducers = combineReducers({
  recoveryPass: recoveryPassReducer,
  resetPass: ResetPassReducer,
});


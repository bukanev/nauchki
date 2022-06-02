import { combineReducers, createStore, applyMiddleware, compose } from 'redux';
import persistReducer from 'redux-persist/es/persistReducer';
import thunk from 'redux-thunk';
import { withoutPersistReducers } from './withoutPersistReducers';
import { withPersistReducers } from './withPersistReducers';
import storage from 'redux-persist/lib/storage';
import persistStore from 'redux-persist/es/persistStore';

const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;

const persistConfig = {
  key: 'root',
  storage,
  blacklist: ['withoutPersist'],
};

const rootReducer = combineReducers({
  withoutPersist: withoutPersistReducers,
  withPersist: persistReducer(persistConfig, withPersistReducers),
});

export const store = createStore(rootReducer, composeEnhancers(applyMiddleware(thunk)));

export const persistor = persistStore(store);

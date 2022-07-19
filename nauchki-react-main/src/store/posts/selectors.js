export const selectPosts = (state) => state.withPersist.posts.posts;
export const selectTags = (state) => state.withPersist.posts.tags;
export const selectAllPosts = (state) => state.withPersist.posts.allPosts;

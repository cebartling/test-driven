import { PATH_MAP, PATH_PROFILE, PATH_WELCOME, PATH_WILDCARD, routes } from '../app-routing.module';
import { MapViewComponent } from '../views/map-view/map-view.component';
import { ProfileViewComponent } from '../views/profile-view/profile-view.component';
import { WelcomeViewComponent } from '../views/welcome-view/welcome-view.component';

describe('App Routing Module', () => {
  describe('routes', () => {
    it('should have a welcome route configured', () => {
      const actualRoute = routes.find((route) => route.path === PATH_WELCOME);
      expect(actualRoute).toBeDefined();
      // @ts-ignore
      expect(actualRoute.component).toBe(WelcomeViewComponent);
    });

    it('should have a profile route configured', () => {
      const actualRoute = routes.find((route) => route.path === PATH_PROFILE);
      expect(actualRoute).toBeDefined();
      // @ts-ignore
      expect(actualRoute.component).toBe(ProfileViewComponent);
    });

    it('should have a map route configured', () => {
      const actualRoute = routes.find((route) => route.path === PATH_MAP);
      expect(actualRoute).toBeDefined();
      // @ts-ignore
      expect(actualRoute.component).toBe(MapViewComponent);
    });

    it('should have a wildcard route configured', () => {
      const actualRoute = routes.find((route) => route.path === PATH_WILDCARD);
      expect(actualRoute).toBeDefined();
      // @ts-ignore
      expect(actualRoute.component).toBe(WelcomeViewComponent);
    });
  });
});

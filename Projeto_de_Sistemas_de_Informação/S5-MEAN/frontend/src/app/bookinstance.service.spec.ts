import { TestBed } from '@angular/core/testing';

import { BookinstanceService } from './bookinstance.service';

describe('BookinstanceService', () => {
  let service: BookinstanceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BookinstanceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

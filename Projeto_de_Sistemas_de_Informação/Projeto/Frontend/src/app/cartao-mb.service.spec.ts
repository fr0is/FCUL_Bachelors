import { TestBed } from '@angular/core/testing';

import { CartaoMBService } from './cartao-mb.service';

describe('CartaoMBService', () => {
  let service: CartaoMBService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CartaoMBService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

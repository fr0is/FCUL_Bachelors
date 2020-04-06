import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BookinstanceDetailComponent } from './bookinstance-detail.component';

describe('BookinstanceDetailComponent', () => {
  let component: BookinstanceDetailComponent;
  let fixture: ComponentFixture<BookinstanceDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BookinstanceDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BookinstanceDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

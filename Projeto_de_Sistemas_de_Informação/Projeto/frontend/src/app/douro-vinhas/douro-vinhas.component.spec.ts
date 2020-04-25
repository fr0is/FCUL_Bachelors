import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DouroVinhasComponent } from './douro-vinhas.component';

describe('DouroVinhasComponent', () => {
  let component: DouroVinhasComponent;
  let fixture: ComponentFixture<DouroVinhasComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DouroVinhasComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DouroVinhasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
